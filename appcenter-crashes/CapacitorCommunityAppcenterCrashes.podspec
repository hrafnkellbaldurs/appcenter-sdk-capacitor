require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapacitorCommunityAppcenterCrashes'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target  = '12.0'
  s.swift_version = '5.1'
  s.static_framework = true
  s.dependency 'Capacitor'
  s.dependency 'AppCenterCapacitorShared', '~> 1.0.0'
  s.dependency 'AppCenter/Crashes', '4.3.0'
end
