import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Doelgroep from './doelgroep';
import DoelgroepDetail from './doelgroep-detail';
import DoelgroepUpdate from './doelgroep-update';
import DoelgroepDeleteDialog from './doelgroep-delete-dialog';

const DoelgroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Doelgroep />} />
    <Route path="new" element={<DoelgroepUpdate />} />
    <Route path=":id">
      <Route index element={<DoelgroepDetail />} />
      <Route path="edit" element={<DoelgroepUpdate />} />
      <Route path="delete" element={<DoelgroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoelgroepRoutes;
