import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Afspraakstatus from './afspraakstatus';
import AfspraakstatusDetail from './afspraakstatus-detail';
import AfspraakstatusUpdate from './afspraakstatus-update';
import AfspraakstatusDeleteDialog from './afspraakstatus-delete-dialog';

const AfspraakstatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Afspraakstatus />} />
    <Route path="new" element={<AfspraakstatusUpdate />} />
    <Route path=":id">
      <Route index element={<AfspraakstatusDetail />} />
      <Route path="edit" element={<AfspraakstatusUpdate />} />
      <Route path="delete" element={<AfspraakstatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AfspraakstatusRoutes;
