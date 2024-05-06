import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overiggebouwdobject from './overiggebouwdobject';
import OveriggebouwdobjectDetail from './overiggebouwdobject-detail';
import OveriggebouwdobjectUpdate from './overiggebouwdobject-update';
import OveriggebouwdobjectDeleteDialog from './overiggebouwdobject-delete-dialog';

const OveriggebouwdobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overiggebouwdobject />} />
    <Route path="new" element={<OveriggebouwdobjectUpdate />} />
    <Route path=":id">
      <Route index element={<OveriggebouwdobjectDetail />} />
      <Route path="edit" element={<OveriggebouwdobjectUpdate />} />
      <Route path="delete" element={<OveriggebouwdobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OveriggebouwdobjectRoutes;
