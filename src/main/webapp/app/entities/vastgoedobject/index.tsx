import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vastgoedobject from './vastgoedobject';
import VastgoedobjectDetail from './vastgoedobject-detail';
import VastgoedobjectUpdate from './vastgoedobject-update';
import VastgoedobjectDeleteDialog from './vastgoedobject-delete-dialog';

const VastgoedobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vastgoedobject />} />
    <Route path="new" element={<VastgoedobjectUpdate />} />
    <Route path=":id">
      <Route index element={<VastgoedobjectDetail />} />
      <Route path="edit" element={<VastgoedobjectUpdate />} />
      <Route path="delete" element={<VastgoedobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VastgoedobjectRoutes;
