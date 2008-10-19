TinyInjector
------------

A tiny references injector for Java.

This very simple injector is able to maintain a pool of objects and inject them into other objects.
The injection works by annotating class fields with @Injectable. Once this is done,
the injector is able to perform the injection through its inject method.

The injector matches fields and objects using string identifiers. When no identifier is specified,
a fully qualified class name is assumed.

If size is not an issue and you need more flexibility, turn to PicoContainer, Google Guice or Spring.
TinyInjector is not a complete IoC library. It is rather meant to cover simple cases in only a few kilo-bytes.

TinyInjector is Copyright (c) 2008 Julien Ponge.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
