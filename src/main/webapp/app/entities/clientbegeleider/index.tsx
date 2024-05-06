import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Clientbegeleider from './clientbegeleider';
import ClientbegeleiderDetail from './clientbegeleider-detail';
import ClientbegeleiderUpdate from './clientbegeleider-update';
import ClientbegeleiderDeleteDialog from './clientbegeleider-delete-dialog';

const ClientbegeleiderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Clientbegeleider />} />
    <Route path="new" element={<ClientbegeleiderUpdate />} />
    <Route path=":id">
      <Route index element={<ClientbegeleiderDetail />} />
      <Route path="edit" element={<ClientbegeleiderUpdate />} />
      <Route path="delete" element={<ClientbegeleiderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClientbegeleiderRoutes;
