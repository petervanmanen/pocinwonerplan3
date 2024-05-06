import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitkeringsrun from './uitkeringsrun';
import UitkeringsrunDetail from './uitkeringsrun-detail';
import UitkeringsrunUpdate from './uitkeringsrun-update';
import UitkeringsrunDeleteDialog from './uitkeringsrun-delete-dialog';

const UitkeringsrunRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitkeringsrun />} />
    <Route path="new" element={<UitkeringsrunUpdate />} />
    <Route path=":id">
      <Route index element={<UitkeringsrunDetail />} />
      <Route path="edit" element={<UitkeringsrunUpdate />} />
      <Route path="delete" element={<UitkeringsrunDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitkeringsrunRoutes;
