import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Deelprocestype from './deelprocestype';
import DeelprocestypeDetail from './deelprocestype-detail';
import DeelprocestypeUpdate from './deelprocestype-update';
import DeelprocestypeDeleteDialog from './deelprocestype-delete-dialog';

const DeelprocestypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Deelprocestype />} />
    <Route path="new" element={<DeelprocestypeUpdate />} />
    <Route path=":id">
      <Route index element={<DeelprocestypeDetail />} />
      <Route path="edit" element={<DeelprocestypeUpdate />} />
      <Route path="delete" element={<DeelprocestypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeelprocestypeRoutes;
