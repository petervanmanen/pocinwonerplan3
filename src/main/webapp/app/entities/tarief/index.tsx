import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tarief from './tarief';
import TariefDetail from './tarief-detail';
import TariefUpdate from './tarief-update';
import TariefDeleteDialog from './tarief-delete-dialog';

const TariefRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tarief />} />
    <Route path="new" element={<TariefUpdate />} />
    <Route path=":id">
      <Route index element={<TariefDetail />} />
      <Route path="edit" element={<TariefUpdate />} />
      <Route path="delete" element={<TariefDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TariefRoutes;
