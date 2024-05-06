import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import B1 from './b-1';
import B1Detail from './b-1-detail';
import B1Update from './b-1-update';
import B1DeleteDialog from './b-1-delete-dialog';

const B1Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<B1 />} />
    <Route path="new" element={<B1Update />} />
    <Route path=":id">
      <Route index element={<B1Detail />} />
      <Route path="edit" element={<B1Update />} />
      <Route path="delete" element={<B1DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default B1Routes;
