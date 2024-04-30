package com.v4vic.ui_kit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.v4vic.designsystem.theme.BibleAppTheme
import com.v4vic.model.Verse

@Composable
fun Greeting(
    name: String,
    verse: Int,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    Row(
        modifier = modifier.padding(paddingValues)
    ) {
        Text(
            text = String.format(verse.format()),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(end = 8.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BibleAppTheme {
        Greeting(
            "Основная тема Евангелия — жизнь и проповедь Иисуса Христа, Сына Божия. Евангелие от Марка наиболее краткое среди четырёх Евангелий. По многим фрагментам оно пересекается с Евангелием от Матфея, однако излагает некоторые события из жизни Иисуса более кратко и в другом порядке. Обращено Евангелие к христианам из язычников — по сравнению с Евангелием от Матфея в тексте присутствуют объяснения иудейских обычаев и географии Палестины, а также намного более редки ссылки на Ветхий Завет. Митрополит Сурожский Антоний писал о нём:",
            1
        )
    }
}

@Composable
fun VersesList(verses: List<Verse>) {
    LazyColumn {
        items(verses) {
            Greeting(it.text, it.verse)
        }
    }
}