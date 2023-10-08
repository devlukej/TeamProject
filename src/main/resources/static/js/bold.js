// bold.js

// 이벤트 핸들러 함수
function makeBold(element) {
    // 모든 .menu-item 클래스를 가진 <a> 태그의 스타일을 초기화
    const menuItems = document.querySelectorAll('.menu-item');
    menuItems.forEach(item => {
        item.style.fontWeight = 'normal';
    });

    // 클릭한 <a> 태그의 스타일을 변경 (볼드체)
    element.style.fontWeight = 'bold';
}
