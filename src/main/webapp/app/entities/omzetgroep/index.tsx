import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Omzetgroep from './omzetgroep';
import OmzetgroepDetail from './omzetgroep-detail';
import OmzetgroepUpdate from './omzetgroep-update';
import OmzetgroepDeleteDialog from './omzetgroep-delete-dialog';

const OmzetgroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Omzetgroep />} />
    <Route path="new" element={<OmzetgroepUpdate />} />
    <Route path=":id">
      <Route index element={<OmzetgroepDetail />} />
      <Route path="edit" element={<OmzetgroepUpdate />} />
      <Route path="delete" element={<OmzetgroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OmzetgroepRoutes;
