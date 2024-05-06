import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Puntengroep from './puntengroep';
import PuntengroepDetail from './puntengroep-detail';
import PuntengroepUpdate from './puntengroep-update';
import PuntengroepDeleteDialog from './puntengroep-delete-dialog';

const PuntengroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Puntengroep />} />
    <Route path="new" element={<PuntengroepUpdate />} />
    <Route path=":id">
      <Route index element={<PuntengroepDetail />} />
      <Route path="edit" element={<PuntengroepUpdate />} />
      <Route path="delete" element={<PuntengroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PuntengroepRoutes;
