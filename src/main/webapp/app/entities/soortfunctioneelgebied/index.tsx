import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortfunctioneelgebied from './soortfunctioneelgebied';
import SoortfunctioneelgebiedDetail from './soortfunctioneelgebied-detail';
import SoortfunctioneelgebiedUpdate from './soortfunctioneelgebied-update';
import SoortfunctioneelgebiedDeleteDialog from './soortfunctioneelgebied-delete-dialog';

const SoortfunctioneelgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortfunctioneelgebied />} />
    <Route path="new" element={<SoortfunctioneelgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<SoortfunctioneelgebiedDetail />} />
      <Route path="edit" element={<SoortfunctioneelgebiedUpdate />} />
      <Route path="delete" element={<SoortfunctioneelgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortfunctioneelgebiedRoutes;
