import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Telefoonstatus from './telefoonstatus';
import TelefoonstatusDetail from './telefoonstatus-detail';
import TelefoonstatusUpdate from './telefoonstatus-update';
import TelefoonstatusDeleteDialog from './telefoonstatus-delete-dialog';

const TelefoonstatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Telefoonstatus />} />
    <Route path="new" element={<TelefoonstatusUpdate />} />
    <Route path=":id">
      <Route index element={<TelefoonstatusDetail />} />
      <Route path="edit" element={<TelefoonstatusUpdate />} />
      <Route path="delete" element={<TelefoonstatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelefoonstatusRoutes;
