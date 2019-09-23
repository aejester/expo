package versioned.host.exp.exponent.modules.universal

import android.content.Context
import expo.modules.errorrecovery.ErrorRecoveryModule
import expo.modules.errorrecovery.RECOVERY_STORE
import host.exp.exponent.kernel.ExperienceId
import org.unimodules.core.ModuleRegistry

class ScopedErrorRecoveryModule(context: Context, val experienceId: ExperienceId) : ErrorRecoveryModule(context) {
  override fun onCreate(moduleRegistry: ModuleRegistry) {
    val suffix = ScopedErrorRecoveryModule::class.java.`package`!!.name.split(".").first()
    mSharedPreferences = context.applicationContext.getSharedPreferences("$RECOVERY_STORE.$suffix", Context.MODE_PRIVATE)
  }

  override fun setRecoveryProps(props: String) {
    mSharedPreferences.edit().putString(experienceId.get(), props).commit()
  }

  override fun consumeRecoveryProps(): String? {
    return mSharedPreferences.getString(experienceId.get(), null)?.let {
      mSharedPreferences.edit().remove(experienceId.get()).commit()
      it
    }
  }
}
