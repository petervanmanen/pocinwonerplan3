import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Activasoort from './activasoort';
import ActivasoortDetail from './activasoort-detail';
import ActivasoortUpdate from './activasoort-update';
import ActivasoortDeleteDialog from './activasoort-delete-dialog';

const ActivasoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Activasoort />} />
    <Route path="new" element={<ActivasoortUpdate />} />
    <Route path=":id">
      <Route index element={<ActivasoortDetail />} />
      <Route path="edit" element={<ActivasoortUpdate />} />
      <Route path="delete" element={<ActivasoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActivasoortRoutes;
