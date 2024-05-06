import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Waterdeel from './waterdeel';
import WaterdeelDetail from './waterdeel-detail';
import WaterdeelUpdate from './waterdeel-update';
import WaterdeelDeleteDialog from './waterdeel-delete-dialog';

const WaterdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Waterdeel />} />
    <Route path="new" element={<WaterdeelUpdate />} />
    <Route path=":id">
      <Route index element={<WaterdeelDetail />} />
      <Route path="edit" element={<WaterdeelUpdate />} />
      <Route path="delete" element={<WaterdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WaterdeelRoutes;
