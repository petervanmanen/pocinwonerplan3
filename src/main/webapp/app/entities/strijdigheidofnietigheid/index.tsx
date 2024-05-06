import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Strijdigheidofnietigheid from './strijdigheidofnietigheid';
import StrijdigheidofnietigheidDetail from './strijdigheidofnietigheid-detail';
import StrijdigheidofnietigheidUpdate from './strijdigheidofnietigheid-update';
import StrijdigheidofnietigheidDeleteDialog from './strijdigheidofnietigheid-delete-dialog';

const StrijdigheidofnietigheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Strijdigheidofnietigheid />} />
    <Route path="new" element={<StrijdigheidofnietigheidUpdate />} />
    <Route path=":id">
      <Route index element={<StrijdigheidofnietigheidDetail />} />
      <Route path="edit" element={<StrijdigheidofnietigheidUpdate />} />
      <Route path="delete" element={<StrijdigheidofnietigheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StrijdigheidofnietigheidRoutes;
