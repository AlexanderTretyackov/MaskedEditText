# MaskedEditText

EditText with ability to set mask. Mask consist of symbols 'X' and same separators. Symbol 'X' represents any not separator symbol.

### Mask examples:
Mask for birthdate format `dd:mm.yyyy` is `XX.XX.XXXX`

Mask for time in format `HH:MM` is `XX:XX`

## Usage
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:layout_margin="20dp">

    <ru.tretyackov.maskededittext.MaskedEditText
        android:id="@+id/maskedEditText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:inputType="number"
        android:maxLength="10"
        android:hint="DD.MM.YYYY"
        android:text="01011990"
        custom:mask="XX.XX.XXXX"/>
</FrameLayout>
```
