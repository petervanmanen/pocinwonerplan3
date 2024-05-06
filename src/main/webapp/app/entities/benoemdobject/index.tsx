import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Benoemdobject from './benoemdobject';
import BenoemdobjectDetail from './benoemdobject-detail';
import BenoemdobjectUpdate from './benoemdobject-update';
import BenoemdobjectDeleteDialog from './benoemdobject-delete-dialog';

const BenoemdobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Benoemdobject />} />
    <Route path="new" element={<BenoemdobjectUpdate />} />
    <Route path=":id">
      <Route index element={<BenoemdobjectDetail />} />
      <Route path="edit" element={<BenoemdobjectUpdate />} />
      <Route path="delete" element={<BenoemdobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenoemdobjectRoutes;
