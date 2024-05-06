import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Doorgeleidingom from './doorgeleidingom';
import DoorgeleidingomDetail from './doorgeleidingom-detail';
import DoorgeleidingomUpdate from './doorgeleidingom-update';
import DoorgeleidingomDeleteDialog from './doorgeleidingom-delete-dialog';

const DoorgeleidingomRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Doorgeleidingom />} />
    <Route path="new" element={<DoorgeleidingomUpdate />} />
    <Route path=":id">
      <Route index element={<DoorgeleidingomDetail />} />
      <Route path="edit" element={<DoorgeleidingomUpdate />} />
      <Route path="delete" element={<DoorgeleidingomDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoorgeleidingomRoutes;
