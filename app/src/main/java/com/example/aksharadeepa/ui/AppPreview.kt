package com.example.aksharadeepa.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aksharadeepa.ui.theme.AksharaDeepaTheme

val PurplePrimary = Color(0xFF673AB7)
val PurpleDark = Color(0xFF512DA8)
val PinkAccent = Color(0xFFFF4081)
val BackgroundGray = Color(0xFFF8F9FA)
val TopicBlueBlack = Color(0xFF1B263B)
val MasteryGreen = Color(0xFF4CAF50)
val GapRed = Color(0xFFF44336)
val IconCircleBg = Color(0xFFD1C4E9)

/**
 * Preview for the Cover Page.
 */
@Preview(showBackground = true, name = "1. Splash Screen")
@Composable
fun PreviewCoverPage() {
    AksharaDeepaTheme {
        CoverPageMockup()
    }
}

/**
 * Preview for the Dashboard.
 */
@Preview(showBackground = true, name = "2. Dashboard")
@Composable
fun PreviewDashboard() {
    AksharaDeepaTheme {
        DashboardMockup()
    }
}

/**
 * Preview for the Analytics.
 */
@Preview(showBackground = true, name = "3. Analytics")
@Composable
fun PreviewAnalytics() {
    AksharaDeepaTheme {
        AnalyticsMockup()
    }
}

/**
 * Preview for the Quiz History.
 */
@Preview(showBackground = true, name = "4. History")
@Composable
fun PreviewHistory() {
    AksharaDeepaTheme {
        HistoryMockup()
    }
}

/**
 * Preview for the Settings.
 */
@Preview(showBackground = true, name = "5. Settings")
@Composable
fun PreviewSettings() {
    AksharaDeepaTheme {
        SettingsMockup()
    }
}

@Composable
fun CoverPageMockup() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.padding(24.dp).size(64.dp),
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "AKSHARA-DEEPA",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Your Path to Excellence",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(scale),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "GET STARTED",
                    color = PurplePrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DashboardMockup() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        // Purple Header covering the title background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PurplePrimary)
                .padding(24.dp)
        ) {
            Text("Home", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Namaste!", color = Color(0xFF1A237E), fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Ready to learn today?", color = Color.Gray, fontSize = 16.sp)
            }

            item {
                SectionHeader("Today's Goal")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = TopicBlueBlack)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Complete at least 1 topic", fontSize = 16.sp, color = Color.White)
                        Text("0/1", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                    }
                }
            }

            item {
                SectionHeader("Focus Areas", color = GapRed)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = TopicBlueBlack)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                             Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFF8A80), modifier = Modifier.size(18.dp))
                             Spacer(modifier = Modifier.width(8.dp))
                             Text("Last Score: 40.0%", color = Color(0xFFFF8A80), fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("1. Arithmetic Progressions", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        TextButton(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text("RETRY QUIZ", color = Color(0xFFFF8A80), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalyticsMockup() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PurplePrimary)
                .padding(24.dp)
        ) {
            Text("Analytics", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Strength Map", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))
            // Simulated Radar Chart
            Box(modifier = Modifier.size(280.dp).background(Color.LightGray.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                Text("51.0", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("Take more quizzes to see your subject mastery!", color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun HistoryMockup() {
    Column(modifier = Modifier.fillMaxSize().background(BackgroundGray)) {
        Box(modifier = Modifier.fillMaxWidth().background(PurplePrimary).padding(24.dp)) {
            Text("History", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { QuizHistoryItem("Arithmetic Progressions", "3/5", "07 May 2024") }
            item { QuizHistoryItem("Chemical Reactions", "2/5", "05 May 2024") }
        }
    }
}

@Composable
fun SettingsMockup() {
    Column(modifier = Modifier.fillMaxSize().background(BackgroundGray)) {
        Box(modifier = Modifier.fillMaxWidth().background(PurplePrimary).padding(24.dp)) {
            Text("Settings", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TopicBlueBlack)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Daily Reminders", color = Color.White, fontWeight = FontWeight.Bold)
                        Switch(checked = true, onCheckedChange = {})
                    }
                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.White.copy(alpha = 0.2f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Reminder Time", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("08:00 PM", color = Color(0xFFD1C4E9), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TopicBlueBlack)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Reset Progress", color = Color(0xFFFF8A80), fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFFF8A80))
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TopicBlueBlack)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("App Info", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Version 1.0.0", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    Text("Lighting the lamp of knowledge", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun QuizHistoryItem(name: String, score: String, date: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = TopicBlueBlack)) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(date, color = Color.LightGray, fontSize = 12.sp)
            }
            Text(score, color = Color(0xFFFFD54F), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, color: Color = Color.Black) {
    Text(text = title, fontSize = 19.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(bottom = 12.dp))
}
