import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gemaal from './gemaal';
import GemaalDetail from './gemaal-detail';
import GemaalUpdate from './gemaal-update';
import GemaalDeleteDialog from './gemaal-delete-dialog';

const GemaalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gemaal />} />
    <Route path="new" element={<GemaalUpdate />} />
    <Route path=":id">
      <Route index element={<GemaalDetail />} />
      <Route path="edit" element={<GemaalUpdate />} />
      <Route path="delete" element={<GemaalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GemaalRoutes;
