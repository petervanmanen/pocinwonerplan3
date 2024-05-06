import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Regelingsoort from './regelingsoort';
import RegelingsoortDetail from './regelingsoort-detail';
import RegelingsoortUpdate from './regelingsoort-update';
import RegelingsoortDeleteDialog from './regelingsoort-delete-dialog';

const RegelingsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Regelingsoort />} />
    <Route path="new" element={<RegelingsoortUpdate />} />
    <Route path=":id">
      <Route index element={<RegelingsoortDetail />} />
      <Route path="edit" element={<RegelingsoortUpdate />} />
      <Route path="delete" element={<RegelingsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegelingsoortRoutes;
