import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Debiteur from './debiteur';
import DebiteurDetail from './debiteur-detail';
import DebiteurUpdate from './debiteur-update';
import DebiteurDeleteDialog from './debiteur-delete-dialog';

const DebiteurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Debiteur />} />
    <Route path="new" element={<DebiteurUpdate />} />
    <Route path=":id">
      <Route index element={<DebiteurDetail />} />
      <Route path="edit" element={<DebiteurUpdate />} />
      <Route path="delete" element={<DebiteurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DebiteurRoutes;
