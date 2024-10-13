describe('Register Account API Test', () => {
  it('should register a new account', () => {
      cy.request({
          method: 'POST',
          url: 'http://localhost:8080/api/account/register',
          body: {
              username: 'docker132',
              password: 'password',
              phoneNumber: '0810627139',
              address: '444/46',
              role: ['user']
          },
          headers: {
              'Content-Type': 'application/json'
          }
      }).then((response) => {
          expect(response.status).to.eq(200);
          expect(response.body).to.have.property('status', 'success');
      });
  });
});
