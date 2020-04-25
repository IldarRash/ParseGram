export function authHeader() {
    // return authorization header with basic auth credentials
    let user = JSON.parse(localStorage.getItem('user'));
    console.log("User ", user);
    if (user && user.authdata) {
        return { 'Authorization': 'Basic ' + user.token };
    } else {
        return {};
    }
}