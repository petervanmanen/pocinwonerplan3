import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Klantbeoordelingreden from './klantbeoordelingreden';
import KlantbeoordelingredenDetail from './klantbeoordelingreden-detail';
import KlantbeoordelingredenUpdate from './klantbeoordelingreden-update';
import KlantbeoordelingredenDeleteDialog from './klantbeoordelingreden-delete-dialog';

const KlantbeoordelingredenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Klantbeoordelingreden />} />
    <Route path="new" element={<KlantbeoordelingredenUpdate />} />
    <Route path=":id">
      <Route index element={<KlantbeoordelingredenDetail />} />
      <Route path="edit" element={<KlantbeoordelingredenUpdate />} />
      <Route path="delete" element={<KlantbeoordelingredenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KlantbeoordelingredenRoutes;
