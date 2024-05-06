import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebiedengroep from './gebiedengroep';
import GebiedengroepDetail from './gebiedengroep-detail';
import GebiedengroepUpdate from './gebiedengroep-update';
import GebiedengroepDeleteDialog from './gebiedengroep-delete-dialog';

const GebiedengroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebiedengroep />} />
    <Route path="new" element={<GebiedengroepUpdate />} />
    <Route path=":id">
      <Route index element={<GebiedengroepDetail />} />
      <Route path="edit" element={<GebiedengroepUpdate />} />
      <Route path="delete" element={<GebiedengroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebiedengroepRoutes;
