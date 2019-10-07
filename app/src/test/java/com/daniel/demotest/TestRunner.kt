package com.daniel.demotest

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = arrayOf("src/test/feature/test.feature"))
class TestRunner