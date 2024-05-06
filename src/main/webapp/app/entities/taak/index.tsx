import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Taak from './taak';
import TaakDetail from './taak-detail';
import TaakUpdate from './taak-update';
import TaakDeleteDialog from './taak-delete-dialog';

const TaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Taak />} />
    <Route path="new" element={<TaakUpdate />} />
    <Route path=":id">
      <Route index element={<TaakDetail />} />
      <Route path="edit" element={<TaakUpdate />} />
      <Route path="delete" element={<TaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TaakRoutes;
