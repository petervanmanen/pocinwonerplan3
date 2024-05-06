import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wozdeelobjectcode from './wozdeelobjectcode';
import WozdeelobjectcodeDetail from './wozdeelobjectcode-detail';
import WozdeelobjectcodeUpdate from './wozdeelobjectcode-update';
import WozdeelobjectcodeDeleteDialog from './wozdeelobjectcode-delete-dialog';

const WozdeelobjectcodeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wozdeelobjectcode />} />
    <Route path="new" element={<WozdeelobjectcodeUpdate />} />
    <Route path=":id">
      <Route index element={<WozdeelobjectcodeDetail />} />
      <Route path="edit" element={<WozdeelobjectcodeUpdate />} />
      <Route path="delete" element={<WozdeelobjectcodeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WozdeelobjectcodeRoutes;
