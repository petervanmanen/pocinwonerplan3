import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verlengingzaak from './verlengingzaak';
import VerlengingzaakDetail from './verlengingzaak-detail';
import VerlengingzaakUpdate from './verlengingzaak-update';
import VerlengingzaakDeleteDialog from './verlengingzaak-delete-dialog';

const VerlengingzaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verlengingzaak />} />
    <Route path="new" element={<VerlengingzaakUpdate />} />
    <Route path=":id">
      <Route index element={<VerlengingzaakDetail />} />
      <Route path="edit" element={<VerlengingzaakUpdate />} />
      <Route path="delete" element={<VerlengingzaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerlengingzaakRoutes;
