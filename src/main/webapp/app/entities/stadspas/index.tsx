import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stadspas from './stadspas';
import StadspasDetail from './stadspas-detail';
import StadspasUpdate from './stadspas-update';
import StadspasDeleteDialog from './stadspas-delete-dialog';

const StadspasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stadspas />} />
    <Route path="new" element={<StadspasUpdate />} />
    <Route path=":id">
      <Route index element={<StadspasDetail />} />
      <Route path="edit" element={<StadspasUpdate />} />
      <Route path="delete" element={<StadspasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StadspasRoutes;
