import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Telefoononderwerp from './telefoononderwerp';
import TelefoononderwerpDetail from './telefoononderwerp-detail';
import TelefoononderwerpUpdate from './telefoononderwerp-update';
import TelefoononderwerpDeleteDialog from './telefoononderwerp-delete-dialog';

const TelefoononderwerpRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Telefoononderwerp />} />
    <Route path="new" element={<TelefoononderwerpUpdate />} />
    <Route path=":id">
      <Route index element={<TelefoononderwerpDetail />} />
      <Route path="edit" element={<TelefoononderwerpUpdate />} />
      <Route path="delete" element={<TelefoononderwerpDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelefoononderwerpRoutes;
