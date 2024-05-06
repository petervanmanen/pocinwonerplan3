import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Hardware from './hardware';
import HardwareDetail from './hardware-detail';
import HardwareUpdate from './hardware-update';
import HardwareDeleteDialog from './hardware-delete-dialog';

const HardwareRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Hardware />} />
    <Route path="new" element={<HardwareUpdate />} />
    <Route path=":id">
      <Route index element={<HardwareDetail />} />
      <Route path="edit" element={<HardwareUpdate />} />
      <Route path="delete" element={<HardwareDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HardwareRoutes;
