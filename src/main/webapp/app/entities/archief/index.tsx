import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Archief from './archief';
import ArchiefDetail from './archief-detail';
import ArchiefUpdate from './archief-update';
import ArchiefDeleteDialog from './archief-delete-dialog';

const ArchiefRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Archief />} />
    <Route path="new" element={<ArchiefUpdate />} />
    <Route path=":id">
      <Route index element={<ArchiefDetail />} />
      <Route path="edit" element={<ArchiefUpdate />} />
      <Route path="delete" element={<ArchiefDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArchiefRoutes;
