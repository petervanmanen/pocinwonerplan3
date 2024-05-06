import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortkunstwerk from './soortkunstwerk';
import SoortkunstwerkDetail from './soortkunstwerk-detail';
import SoortkunstwerkUpdate from './soortkunstwerk-update';
import SoortkunstwerkDeleteDialog from './soortkunstwerk-delete-dialog';

const SoortkunstwerkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortkunstwerk />} />
    <Route path="new" element={<SoortkunstwerkUpdate />} />
    <Route path=":id">
      <Route index element={<SoortkunstwerkDetail />} />
      <Route path="edit" element={<SoortkunstwerkUpdate />} />
      <Route path="delete" element={<SoortkunstwerkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortkunstwerkRoutes;
