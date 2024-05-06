import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inburgeraar from './inburgeraar';
import InburgeraarDetail from './inburgeraar-detail';
import InburgeraarUpdate from './inburgeraar-update';
import InburgeraarDeleteDialog from './inburgeraar-delete-dialog';

const InburgeraarRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inburgeraar />} />
    <Route path="new" element={<InburgeraarUpdate />} />
    <Route path=":id">
      <Route index element={<InburgeraarDetail />} />
      <Route path="edit" element={<InburgeraarUpdate />} />
      <Route path="delete" element={<InburgeraarDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InburgeraarRoutes;
