import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sociaalteamdossiersoort from './sociaalteamdossiersoort';
import SociaalteamdossiersoortDetail from './sociaalteamdossiersoort-detail';
import SociaalteamdossiersoortUpdate from './sociaalteamdossiersoort-update';
import SociaalteamdossiersoortDeleteDialog from './sociaalteamdossiersoort-delete-dialog';

const SociaalteamdossiersoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sociaalteamdossiersoort />} />
    <Route path="new" element={<SociaalteamdossiersoortUpdate />} />
    <Route path=":id">
      <Route index element={<SociaalteamdossiersoortDetail />} />
      <Route path="edit" element={<SociaalteamdossiersoortUpdate />} />
      <Route path="delete" element={<SociaalteamdossiersoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SociaalteamdossiersoortRoutes;
