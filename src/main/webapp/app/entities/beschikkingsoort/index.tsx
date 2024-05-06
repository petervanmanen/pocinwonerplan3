import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beschikkingsoort from './beschikkingsoort';
import BeschikkingsoortDetail from './beschikkingsoort-detail';
import BeschikkingsoortUpdate from './beschikkingsoort-update';
import BeschikkingsoortDeleteDialog from './beschikkingsoort-delete-dialog';

const BeschikkingsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beschikkingsoort />} />
    <Route path="new" element={<BeschikkingsoortUpdate />} />
    <Route path=":id">
      <Route index element={<BeschikkingsoortDetail />} />
      <Route path="edit" element={<BeschikkingsoortUpdate />} />
      <Route path="delete" element={<BeschikkingsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeschikkingsoortRoutes;
