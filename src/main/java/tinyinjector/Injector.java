/*
    Copyright (c) 2008 Julien Ponge

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package tinyinjector;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;

/**
 * A tiny references injector for Java.
 * <p/>
 * This very simple injector is able to maintain a pool of objects and inject them into other objects.
 * The injection works by annotating class fields with <code>@Injectable</code>. Once this is done,
 * the injector is able to perform the injection through its <code>inject</code> method.
 * <p/>
 * The injector matches fields and objects using string identifiers. When no identifier is specified,
 * a fully qualified class name is assumed.
 * <p/>
 * If size is not an issue and you need more flexibility, turn to PicoContainer, Google Guice or Spring.
 * TinyInjector is not a complete IoC library. It is rather meant to cover simple cases in only a few kilo-bytes.
 *
 * @author Julien Ponge
 */
public class Injector
{
    private Map<String, Object> instances = new HashMap<String, Object>();

    /**
     * Registers an object using an identifier.
     *
     * @param id       the object identifier
     * @param instance the object instance
     * @return the injector instance, to give it a fluent interface
     */
    public Injector register(String id, Object instance)
    {
        instances.put(id, instance);
        return this;
    }

    /**
     * Registers an object by class. This is the same as
     * <pre>
     *      injector.register(clazz.getCanonicalName(), instance);
     * </pre>
     *
     * @param clazz    the object class
     * @param instance the object instance
     * @return the injector instance, to give it a fluent interface
     */
    public Injector register(Class clazz, Object instance)
    {
        return register(clazz.getCanonicalName(), instance);
    }

    /**
     * Registers an object with its identifier beeing its qualified class name.
     *
     * @param instance the object instance
     * @return the injector instance, to give it a fluent interface
     */
    public Injector register(Object instance)
    {
        return register(instance.getClass(), instance);
    }

    /**
     * Removes an object from the injector.
     *
     * @param id the object identifier
     * @return the injector instance, to give it a fluent interface
     */
    public Injector unregister(String id)
    {
        instances.remove(id);
        return this;
    }

    /**
     * Performs an injection.
     *
     * @param object the object to perform the injection on
     * @return the injector instance, to give it a fluent interface
     * @throws IllegalAccessException
     * @throws InvalidInjectionReferenceException
     *                                in case a requested identifier reference is unknown to the injector
     */
    public Injector inject(Object object) throws IllegalAccessException, InvalidInjectionReferenceException
    {
        for (Field field : object.getClass().getDeclaredFields())
        {
            if (field.isAnnotationPresent(Injectable.class))
            {
                String reference;
                Injectable injectable = field.getAnnotation(Injectable.class);

                if (!injectable.id().equals(""))
                {
                    reference = injectable.id();
                }
                else
                {
                    reference = field.getType().getCanonicalName();
                }
                if (!instances.containsKey(reference))
                {
                    throw new InvalidInjectionReferenceException("Unknown reference id for injecting: " + reference);
                }

                field.setAccessible(true);
                field.set(object, instances.get(reference));
            }
        }
        return this;
    }
}
