import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Periode from './periode';
import PeriodeDetail from './periode-detail';
import PeriodeUpdate from './periode-update';
import PeriodeDeleteDialog from './periode-delete-dialog';

const PeriodeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Periode />} />
    <Route path="new" element={<PeriodeUpdate />} />
    <Route path=":id">
      <Route index element={<PeriodeDetail />} />
      <Route path="edit" element={<PeriodeUpdate />} />
      <Route path="delete" element={<PeriodeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PeriodeRoutes;
