import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bedrijfsprocestype from './bedrijfsprocestype';
import BedrijfsprocestypeDetail from './bedrijfsprocestype-detail';
import BedrijfsprocestypeUpdate from './bedrijfsprocestype-update';
import BedrijfsprocestypeDeleteDialog from './bedrijfsprocestype-delete-dialog';

const BedrijfsprocestypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bedrijfsprocestype />} />
    <Route path="new" element={<BedrijfsprocestypeUpdate />} />
    <Route path=":id">
      <Route index element={<BedrijfsprocestypeDetail />} />
      <Route path="edit" element={<BedrijfsprocestypeUpdate />} />
      <Route path="delete" element={<BedrijfsprocestypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BedrijfsprocestypeRoutes;
