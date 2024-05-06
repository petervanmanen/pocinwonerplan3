import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lijnengroep from './lijnengroep';
import LijnengroepDetail from './lijnengroep-detail';
import LijnengroepUpdate from './lijnengroep-update';
import LijnengroepDeleteDialog from './lijnengroep-delete-dialog';

const LijnengroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lijnengroep />} />
    <Route path="new" element={<LijnengroepUpdate />} />
    <Route path=":id">
      <Route index element={<LijnengroepDetail />} />
      <Route path="edit" element={<LijnengroepUpdate />} />
      <Route path="delete" element={<LijnengroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LijnengroepRoutes;
