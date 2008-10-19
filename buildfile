# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#  http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

require 'buildr/groovy/bdd'

VERSION_NUMBER = "0.0.0"
GROUP = "tinyinjector"
COPYRIGHT = "Copyright (c) 2008 Julien Ponge"

repositories.remote << "http://www.ibiblio.org/maven2/"

desc "The Tinyinjector project"
define "tinyinjector" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  
  manifest["Implementation-Vendor"] = COPYRIGHT
  
  compile.options.source = '1.5'
  compile.options.target = '1.5'
  
  test.using :easyb
  
  package :jar
  package :javadoc
  
  package(:zip).path("tinyinjector-#{VERSION_NUMBER}").tap do |path|
    path.include _("target/tinyinjector-#{VERSION_NUMBER}.jar")
    path.include _("target/javadoc")
    path.include 'buildfile', 'LICENSE.txt', 'README.txt', 'src'
  end

end
