import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leerjaar from './leerjaar';
import LeerjaarDetail from './leerjaar-detail';
import LeerjaarUpdate from './leerjaar-update';
import LeerjaarDeleteDialog from './leerjaar-delete-dialog';

const LeerjaarRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leerjaar />} />
    <Route path="new" element={<LeerjaarUpdate />} />
    <Route path=":id">
      <Route index element={<LeerjaarDetail />} />
      <Route path="edit" element={<LeerjaarUpdate />} />
      <Route path="delete" element={<LeerjaarDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeerjaarRoutes;
