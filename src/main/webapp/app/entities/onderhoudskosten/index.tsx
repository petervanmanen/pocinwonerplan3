import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderhoudskosten from './onderhoudskosten';
import OnderhoudskostenDetail from './onderhoudskosten-detail';
import OnderhoudskostenUpdate from './onderhoudskosten-update';
import OnderhoudskostenDeleteDialog from './onderhoudskosten-delete-dialog';

const OnderhoudskostenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderhoudskosten />} />
    <Route path="new" element={<OnderhoudskostenUpdate />} />
    <Route path=":id">
      <Route index element={<OnderhoudskostenDetail />} />
      <Route path="edit" element={<OnderhoudskostenUpdate />} />
      <Route path="delete" element={<OnderhoudskostenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderhoudskostenRoutes;
