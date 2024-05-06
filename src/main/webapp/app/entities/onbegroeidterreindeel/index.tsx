import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onbegroeidterreindeel from './onbegroeidterreindeel';
import OnbegroeidterreindeelDetail from './onbegroeidterreindeel-detail';
import OnbegroeidterreindeelUpdate from './onbegroeidterreindeel-update';
import OnbegroeidterreindeelDeleteDialog from './onbegroeidterreindeel-delete-dialog';

const OnbegroeidterreindeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onbegroeidterreindeel />} />
    <Route path="new" element={<OnbegroeidterreindeelUpdate />} />
    <Route path=":id">
      <Route index element={<OnbegroeidterreindeelDetail />} />
      <Route path="edit" element={<OnbegroeidterreindeelUpdate />} />
      <Route path="delete" element={<OnbegroeidterreindeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnbegroeidterreindeelRoutes;
