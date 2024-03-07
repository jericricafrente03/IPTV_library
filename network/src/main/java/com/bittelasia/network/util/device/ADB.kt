package com.bittelasia.network.util.device

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object ADB {

    suspend fun executeShellCommand(command: String): String = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            process.waitFor()
            output.toString()
        } catch (e: Exception) {
            "Error executing command: ${e.message}"
        }
    }

    suspend fun exec(adb: String) = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec(adb)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val log = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                log.append(line + "\n")
            }
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}