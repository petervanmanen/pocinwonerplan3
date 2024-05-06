import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wozdeelobject from './wozdeelobject';
import WozdeelobjectDetail from './wozdeelobject-detail';
import WozdeelobjectUpdate from './wozdeelobject-update';
import WozdeelobjectDeleteDialog from './wozdeelobject-delete-dialog';

const WozdeelobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wozdeelobject />} />
    <Route path="new" element={<WozdeelobjectUpdate />} />
    <Route path=":id">
      <Route index element={<WozdeelobjectDetail />} />
      <Route path="edit" element={<WozdeelobjectUpdate />} />
      <Route path="delete" element={<WozdeelobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WozdeelobjectRoutes;
