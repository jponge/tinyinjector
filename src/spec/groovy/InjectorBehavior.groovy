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

import tinyinjector.*

class A
{
    public String hello()
    {
        return "hello"
    }
}

class B
{
    @Injectable A first
    @Injectable(id = "a") A second

    public String one()
    {
        return first.hello()
    }

    public String two()
    {
        return second.hello()
    }
}

class C
{
    @Injectable(id = "foo") A a
}

Injector injector =  new Injector()
injector.register(A.class, new A())
        .register("a", new A())

it "should inject by class reference", {
    B b = new B()
    injector.inject(b)
    b.one().shouldBe "hello"
}

it "should inject by string reference", {
    B b = new B()
    injector.inject(b)
    b.two().shouldBe "hello"
}



/* Fails on an outdated easyb library that BuildR 1.3.3 gets...
it "should throw an exception on an unknow injection reference", {
    C c = new C()
    ensureThrows(InvalidInjectionReferenceException) {
        injector.inject(c)
    }
}*/
