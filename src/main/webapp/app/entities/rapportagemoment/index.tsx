import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rapportagemoment from './rapportagemoment';
import RapportagemomentDetail from './rapportagemoment-detail';
import RapportagemomentUpdate from './rapportagemoment-update';
import RapportagemomentDeleteDialog from './rapportagemoment-delete-dialog';

const RapportagemomentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rapportagemoment />} />
    <Route path="new" element={<RapportagemomentUpdate />} />
    <Route path=":id">
      <Route index element={<RapportagemomentDetail />} />
      <Route path="edit" element={<RapportagemomentUpdate />} />
      <Route path="delete" element={<RapportagemomentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RapportagemomentRoutes;
