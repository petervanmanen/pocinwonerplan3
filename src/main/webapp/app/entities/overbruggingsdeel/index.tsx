import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overbruggingsdeel from './overbruggingsdeel';
import OverbruggingsdeelDetail from './overbruggingsdeel-detail';
import OverbruggingsdeelUpdate from './overbruggingsdeel-update';
import OverbruggingsdeelDeleteDialog from './overbruggingsdeel-delete-dialog';

const OverbruggingsdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overbruggingsdeel />} />
    <Route path="new" element={<OverbruggingsdeelUpdate />} />
    <Route path=":id">
      <Route index element={<OverbruggingsdeelDetail />} />
      <Route path="edit" element={<OverbruggingsdeelUpdate />} />
      <Route path="delete" element={<OverbruggingsdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverbruggingsdeelRoutes;
